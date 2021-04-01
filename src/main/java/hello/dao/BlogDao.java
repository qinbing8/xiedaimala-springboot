package hello.dao;

import hello.entity.Blog;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

@Service
public class BlogDao {
    private SqlSession sqlSession;

    @Autowired
    public BlogDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Blog> getBlogs(Integer page, Integer pageSize, Integer userId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", userId);
        parameters.put("offset", (page - 1) * pageSize);
        parameters.put("limit", pageSize);
        return sqlSession.selectList("selectBlog", parameters);
    }

    public int count(Integer userId) {
        return sqlSession.selectOne("countBlog", userId);
    }

}
