package hello.entity;

import java.util.List;

public class BlogResult extends Result<Blog> {

    public static BlogResult success(Blog data) {
        return new BlogResult(ResultStatus.OK, "获取成功", data);
    }

    public static BlogResult success(String msg, Blog data) {
        return new BlogResult(ResultStatus.OK, msg, data);
    }

    public static BlogResult failure(String msg) {
        return new BlogResult(ResultStatus.FAIL, msg, null);
    }

    protected BlogResult(ResultStatus status, String msg, Blog data) {
        super(status, msg, data);
    }

    public static BlogResult failure(IllegalArgumentException e) {
        return new BlogResult(ResultStatus.FAIL, e.getMessage(), null);
    }
}
