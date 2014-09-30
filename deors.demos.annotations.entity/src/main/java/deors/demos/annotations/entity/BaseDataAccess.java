package deors.demos.annotations.entity;

public interface BaseDataAccess<T> {

    T getById(T id);
    T insert(T newRecord);
    T update(T updateRecord);
    void delete(T deleteRecord);
}
