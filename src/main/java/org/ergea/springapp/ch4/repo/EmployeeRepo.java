package org.ergea.springapp.ch4.repo;

import org.ergea.springapp.ch4.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    @Query(value = "SELECT * FROM getemployeelist()", nativeQuery = true)
    List<Object[]> getEmployeeList();

    @Modifying
    @Procedure("createemployee")
    void createEmployee();

    @Query(value = "select uk from Employee uk WHERE uk.id = :id")
    public Employee getById(@Param("id") Long id);

    @Query(value = "select uk from Employee uk WHERE uk.name = :nameParameter")
    public Employee getByName(@Param("nameParameter") String name);

    @Query("FROM Employee u WHERE LOWER(u.name) like LOWER(:nameParam)")
    public List<Employee> findByNameLike(String nameParam);

    /*
  4. Pagination -> Memecah data -> 1000 -> 10 data ada 100 halaman
   */
//    menampilkan semua data : pagination
    @Query("FROM Employee u")
    public Page<Employee> getAllDataPage(Pageable pageable);

    // menampikan semua data : tapi filter base name
    @Query("FROM Employee u where LOWER(u.name) like LOWER(:nameParam)")
    public Page<Employee> getAllDataByName(@Param("nameParam") String name, Pageable pageable);
}
