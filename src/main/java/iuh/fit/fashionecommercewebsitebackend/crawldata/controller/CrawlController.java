package iuh.fit.fashionecommercewebsitebackend.crawldata.controller;

import iuh.fit.fashionecommercewebsitebackend.crawldata.res.TikiRes;
import iuh.fit.fashionecommercewebsitebackend.crawldata.service.CrawlDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/crawl")
public class CrawlController {

    private final CrawlDataService crawlDataService;

    @GetMapping
    public TikiRes getProducts(@RequestParam String url,
                               @RequestParam int[] brands,
                               @RequestParam int category,
                               @RequestParam int[] providers,
                               @RequestParam int[] size,
                               @RequestParam int[] color) {
        return crawlDataService.getProducts(url, brands, category, providers, size, color);
    }

    @GetMapping("/preview")
    public TikiRes previewData(@RequestParam String url) {
        return crawlDataService.previewData(url);
    }
}
