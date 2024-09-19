package iuh.fit.fashionecommercewebsitebackend.services.impl;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.PageResponse;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.repositories.customizations.BaseCustomizationRepository;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.BaseService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class BaseServiceImpl<T, ID extends Serializable>
        extends BaseCustomizationRepository<T>
        implements BaseService<T, ID>{

    private final JpaRepository<T, ID> repository;

    public BaseServiceImpl(JpaRepository<T, ID> repository, Class<T> entityClass) {
        super(entityClass);
        this.repository = repository;
    }

    @Override
    public T save(T t) {
        return repository.save(t);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }


    @Override
    public T update(ID id, T t) {
        repository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        return repository.save(t);
    }

    @Override
    public T updatePatch(ID id, Map<String, ?> data) {
        T t = repository.findById(id).orElseThrow(()-> new DataNotFoundException("Not found"));
        Class<?> clazz = t.getClass();
        Set<String> keys = data.keySet();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            for (String key : keys) {
                if (method.getName().equals("set" + toUpperCaseFirstChar(key)) ||
                        method.getName().equals("is" + toUpperCaseFirstChar(key))){
                    try {
                        Object value = data.get(key);
                        if (value instanceof String && method.getParameterTypes()[0].isEnum()) {
                            value = Enum.valueOf((Class<Enum>) method.getParameterTypes()[0], (String) value);
                        }
                        method.invoke(t, value);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return repository.save(t);
    }

    private String toUpperCaseFirstChar(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    @Override
    public void deleteById(ID id) {
        Optional<T> t = repository.findById(id);
        if (t.isEmpty()) {
            throw new DataNotFoundException( "Not found");
        }
        repository.deleteById(id);
    }

    @Override
    public PageResponse<T> getDataWithPage(int pageNo, int pageSize, String[] search, String[] sortBy) {
        return (PageResponse<T>) super.getDataWithPage(pageNo, pageSize, search, sortBy);
    }
}
