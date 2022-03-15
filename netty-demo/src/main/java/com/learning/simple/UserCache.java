package com.learning.simple;

import io.netty.util.Recycler;

/**
 * 对象池
 *
 * @author : 刘宇泽
 * @date : 2022/3/8 14:44
 */
public class UserCache {

    private static final Recycler<User> userRecycler = new Recycler<User>() {

        @Override
        protected User newObject(Handle<User> handle) {
            return new User(handle);
        }
    };

    static class User {
        private String name;

        private Recycler.Handle<User> handle;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public User(Recycler.Handle<User> handle) {
            this.handle = handle;
        }

        public void recycle() {
            this.handle.recycle(this);
        }
    }

    public static void main(String[] args) {
        // 1、从对象池获取 User 对象
        User user1 = userRecycler.get();
        user1.setName("name");
        user1.recycle();
        User user2 = userRecycler.get();
        System.out.println(user2.getName());
        System.out.println(user1 == user2);
    }
}
