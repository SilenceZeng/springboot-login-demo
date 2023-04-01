package com.example.login.entity;

import java.util.List;

public class BlogResult extends Result<List<Blog>> {
    private int total;
    private int page;
    private int totalPage;

    public static BlogResult successBlogs(List<Blog> data, int total, int page, int totalPage) {
        return new BlogResult("ok", "获取成功", data, total, page, totalPage);
    }

    public static BlogResult failure(String message) {
        return new BlogResult("fail", message, null, 0, 0, 0);
    }

    protected BlogResult(String status, String message, List<Blog> data, int total, int page, int totalPage) {
        super(status, message, data);
        this.total = total;
        this.page = page;
        this.totalPage = totalPage;
    }

    public int getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPage() {
        return totalPage;
    }
}
