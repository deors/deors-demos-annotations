package deors.demos.annotations.entity;

public interface BaseDataAccess<T, K> {

    T selectById(K id);
    T insert(T newRecord);
    void update(T updatedRecord);
    void delete(T deletedRecord);
}
