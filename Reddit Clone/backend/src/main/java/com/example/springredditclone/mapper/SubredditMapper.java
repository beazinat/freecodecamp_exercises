package com.example.springredditclone.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.springredditclone.dto.SubredditDto;
import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.Subreddit;

// This class is responsible for mapping between Subreddit and SubredditDto. It uses MapStruct to generate the implementation at compile time, which improves performance and reduces boilerplate code. The mapper also calculates the number of posts in a subreddit by counting the size of the posts list. The critical part of this mapper is the use of the `@Mapping` annotation to specify how fields should be mapped, including an expression to calculate the number of posts directly from the list of posts in the Subreddit model.
@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto mapSubredditToDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Subreddit mapDtoToSubreddit(SubredditDto subredditDto);
}