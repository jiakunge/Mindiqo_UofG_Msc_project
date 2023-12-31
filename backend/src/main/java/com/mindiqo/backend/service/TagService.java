package com.mindiqo.backend.service;

import com.mindiqo.backend.entity.Tag;

import java.util.List;

public interface TagService {

    List<Tag> getAll();

    void add(Tag tag);
}
