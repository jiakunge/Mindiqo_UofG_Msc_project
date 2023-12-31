package com.mindiqo.backend.service.impl;

import com.mindiqo.backend.entity.Tag;
import com.mindiqo.backend.mapper.TagMapper;
import com.mindiqo.backend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;


    @Override
    public List<Tag> getAll() {
        return tagMapper.getAll();
    }

    @Override
    public void add(Tag tag) {
        tagMapper.insert(tag);
    }
}
