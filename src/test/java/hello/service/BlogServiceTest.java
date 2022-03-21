package hello.service;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import hello.dao.BlogDao;
import hello.entity.BlogResult;
import hello.entity.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {
    @Mock
    BlogDao blogDao;
    @InjectMocks
    BlogService blogService;

    @Test
    public void getBlogsFromDb() {
        blogService.getBlogs(1, 10, null);

        verify(blogDao).getBlogs(1, 10, null);
    }

    @Test
    public void returnFailureWhenExceptionThrown() {
        when(blogDao.getBlogs(anyInt(), anyInt(), any())).thenThrow(new RuntimeException());

        Result result = blogService.getBlogs(1, 10, null);

        Assertions.assertEquals("fail", result.getStatus());
        Assertions.assertEquals("系统异常", result.getMsg());
    }

    @Test
    public void getBlogByIdTest() {
        blogService.getBlogById(1);

        verify(blogDao).selectBlogById(1);
    }

    @Test
    public void getBlogByIdReturnFailureWhenExceptionThrown() {
        when(blogDao.selectBlogById(anyInt())).thenThrow(new RuntimeException());

        Result blog = blogService.getBlogById(1);

        Assertions.assertEquals("fail", blog.getStatus());
        Assertions.assertEquals("系统异常", blog.getMsg());
    }

}
