package com.firesale.api.mockdata;

import com.firesale.api.model.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagData {
    public static List<Tag> getTags()
    {
        List<Tag> tags = new ArrayList<>();
        for(int i = 1; i <= 10; i++)
        {
            Tag tag = new Tag();
            tag.setName("Test tag " + i);
            tag.setId((long) i);
            tags.add(tag);
        }
        return tags;
    }
}
