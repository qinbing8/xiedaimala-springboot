package hello.service;

import hello.dao.BlogDao;
import hello.entity.Blog;
import hello.entity.BlogListResult;
import hello.entity.BlogResult;
import hello.entity.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

@Service
public class BlogService {
    private BlogDao blogDao;
    private UserService userService;

    @Inject
    public BlogService(BlogDao blogDao, UserService userService) {
        this.blogDao = blogDao;
        this.userService = userService;
    }

    public BlogListResult getBlogs(Integer page, Integer pageSize, Integer userId) {
        try {
            List<Blog> blogs = blogDao.getBlogs(page, pageSize, userId);

            int count = blogDao.count(userId);

            // count = 3 / pageSize = 2 / page = 2
            int pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;

            return BlogListResult.success(blogs, count, page, pageCount);
        } catch (Exception e) {
            e.printStackTrace();
            return BlogListResult.failure("系统异常");
        }
    }

    public BlogResult getBlogById(Integer blogId) {
        try {
            return BlogResult.success(blogDao.selectBlogById(blogId));
        } catch (Exception e) {
            return BlogResult.failure("系统异常");
        }
    }

    public BlogResult insertBlog(Blog newBlog) {
        try {
            return BlogResult.success("创建成功", blogDao.insertBlog(newBlog));
        } catch (IllegalArgumentException e) {
            return BlogResult.failure(e);
        }
    }


    public BlogResult updateBlog(int blogId, Blog targetBlog) {

        Blog blogDb = blogDao.selectBlogById(blogId);
        if (blogDb == null) {
            return BlogResult.failure("博客不存在");
        }

        if (!Objects.equals(blogId, blogDb.getId())) {
            return BlogResult.failure("无法修改别人的博客");
        }
        try {
            targetBlog.setId(blogId);
            return BlogResult.success("修改成功", blogDao.updateBlog(targetBlog));
        } catch (Exception e) {
            return BlogResult.failure(e);
        }
    }

    public BlogResult deleteBlog(int blogId, User user) {
        Blog blogDb = blogDao.selectBlogById(blogId);
        System.out.println(blogDb);
        if (blogDb == null) {
            return BlogResult.failure("博客不存在");
        }

        if (!Objects.equals(user.getId(), blogDb.getUserId())) {
            return BlogResult.failure("无法修改别人的博客");
        }
        try {
            blogDao.delteBlog(blogId);
            return BlogResult.success("删除成功", null);
        } catch (Exception e) {
            return BlogResult.failure(e);
        }
    }
}
