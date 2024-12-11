package iuh.fit.fashionecommercewebsitebackend.services.impl;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.socket.CommentDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.PageResponse;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.socket.CommentResponse;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.api.mappers.sockets.CommentMapper;
import iuh.fit.fashionecommercewebsitebackend.models.Comment;
import iuh.fit.fashionecommercewebsitebackend.models.CommentMedia;
import iuh.fit.fashionecommercewebsitebackend.models.Product;
import iuh.fit.fashionecommercewebsitebackend.models.enums.MediaType;
import iuh.fit.fashionecommercewebsitebackend.repositories.CommentMediaRepository;
import iuh.fit.fashionecommercewebsitebackend.repositories.CommentRepository;
import iuh.fit.fashionecommercewebsitebackend.repositories.ProductRepository;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.socket.CommentService;
import iuh.fit.fashionecommercewebsitebackend.utils.S3Upload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment, String> implements CommentService {
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final S3Upload s3Upload;
    private final CommentMediaRepository commentMediaRepository;
    private static final Set<String> IMAGE_EXTENSIONS = new HashSet<>(Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "tiff", "webp"));
    private static final Set<String> VIDEO_EXTENSIONS = new HashSet<>(Arrays.asList("mp4", "avi", "mov", "wmv", "flv", "mkv", "webm"));
    private final ProductRepository productRepository;

    public CommentServiceImpl(CommentMapper commentMapper,
                              CommentRepository commentRepository,
                              S3Upload s3Upload,
                              CommentMediaRepository commentMediaRepository,
                              ProductRepository productRepository) {
        super(commentRepository, Comment.class);
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
        this.s3Upload = s3Upload;
        this.commentMediaRepository = commentMediaRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(rollbackFor = {DataNotFoundException.class, IOException.class})
    public CommentResponse createComment(CommentDto commentDto) throws DataNotFoundException, IOException {
        Product product = productRepository.findById(commentDto.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        Comment comment = commentMapper.commentDtoToComment(commentDto);
        commentRepository.save(comment);
        List<CommentMedia> commentMediaList = null;
        if(commentDto.getMedia() != null) {
            commentMediaList = uploadMedia(commentDto.getMedia(), comment);
        }
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setComment(comment);
        commentResponse.setCommentMedia(commentMediaList);
        calculateRating(product);
        return commentResponse;
    }

    @Override
    public PageResponse<List<CommentResponse>> getPageData(int pageNo, int pageSize, String[] search, String[] sort) {
        PageResponse<?> pageResponse = super.getPageData(pageNo, pageSize, search, sort);
        List<Comment> comments = (List<Comment>) pageResponse.getData();
        List<CommentResponse> commentResponses = comments.stream().map(cmt ->
                        CommentResponse.builder()
                                .comment(cmt)
                                .commentMedia(commentMediaRepository.findAllByCommentId(cmt.getId()))
                                .build())
                .toList();
        PageResponse<List<CommentResponse>> result = new PageResponse<>();
        result.setData(commentResponses);
        result.setPageNo(pageResponse.getPageNo());
        result.setTotalPage(pageResponse.getTotalPage());
        result.setTotalElements(pageResponse.getTotalElements());
        return result;
    }


    private List<CommentMedia> uploadMedia(List<MultipartFile> lists, Comment comment) throws IOException {
        List<CommentMedia> commentMediaList = new ArrayList<>();
        for (MultipartFile file : lists) {
            String path = s3Upload.uploadFile(file);
            String id = UUID.randomUUID().toString();
            CommentMedia commentMedia = CommentMedia.builder()
                    .id(id)
                    .comment(comment)
                    .path(path)
                    .mediaType(getMedia(file))
                    .build();
            commentMediaList.add(commentMediaRepository.save(commentMedia));
        }
        return commentMediaList;
    }

    private MediaType getMedia(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (IMAGE_EXTENSIONS.contains(extension)) {
            return MediaType.IMAGE;
        } else if (VIDEO_EXTENSIONS.contains(extension)) {
            return MediaType.VIDEO;
        }
        throw new IllegalArgumentException("File type not supported");
    }

    private void calculateRating(Product product) {
        List<Comment> comments = commentRepository.findAllByProductId(product.getId());
        float totalRating = 0;
        for (Comment comment : comments) {
            totalRating += comment.getRating();
        }
        float averageRating = totalRating / comments.size();
        product.setAvgRating(averageRating);
        product.setNumberOfRating(comments.size());
        productRepository.save(product);
    }
}
