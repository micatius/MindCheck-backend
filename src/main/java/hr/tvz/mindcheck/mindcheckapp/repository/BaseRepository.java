package hr.tvz.mindcheck.mindcheckapp.repository;

public interface BaseRepository<T> {
    public T save(T entity);
}
