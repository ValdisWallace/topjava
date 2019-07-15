package ru.javawebinar.topjava.service;

public abstract class AbstractServiceTest {

    abstract void create() throws Exception;

    abstract void delete() throws Exception;

    abstract void get() throws Exception;

    abstract void getNotFound() throws Exception;

    abstract void update() throws Exception;

    abstract void getAll() throws Exception;
}
