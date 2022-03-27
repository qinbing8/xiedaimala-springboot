package hello.dao;

import hello.entity.Blog;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogDao {
    private SqlSession sqlSession;

    @Autowired
    public BlogDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Blog> getBlogs(Integer page, Integer pageSize, Integer userId) {
        Map<String, Object> parameters = asMap(
                "user_id", userId,
                "offset", (page - 1) * pageSize,
                "limit", pageSize);
        return sqlSession.selectList("selectBlog", parameters);
    }

    public int count(Integer userId) {
        return sqlSession.selectOne("countBlog", asMap("userId", userId));
    }

    public Blog selectBlogById(Integer id) {
        return sqlSession.selectOne("selectBlogById", asMap("id", id));
    }

    private Map<String, Object> asMap(Object... args) {
        Map<String, Object> result = new HashMap<>();
        for (int i = 0; i < args.length; i += 2) {
            result.put(args[i].toString(), args[i + 1]);
        }
        return result;
    }

    public Blog insertBlog(Blog newBlog) {
        sqlSession.insert("insertBlog", newBlog);
        return selectBlogById(newBlog.getId());
    }

    public Blog updateBlog(Blog targetBlgo) {
        sqlSession.update("updateBlog", targetBlgo);
        return selectBlogById(targetBlgo.getId());
    }

    public void delteBlog(int blogId) {
        sqlSession.delete("deleteBlog", blogId);
    }
}
