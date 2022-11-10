package id.co.nds.catalogue.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import id.co.nds.catalogue.entities.UserEntity;
import id.co.nds.catalogue.globals.GlobalConstant;

@Repository
@Transactional
public interface UserRepo extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity> {
    @Query(value = "SELECT COUNT(*) FROM ms_user WHERE rec_status = '"
        + GlobalConstant.REC_STATUS_ACTIVE
        + "' AND LOWER(fullname) = LOWER(:fullname)", nativeQuery = true)
    long countByFullname(@Param("fullname") String fullname);

    @Query(value = "SELECT COUNT(*) FROM ms_user WHERE rec_status = '"
        + GlobalConstant.REC_STATUS_ACTIVE
        + "' AND REPLACE(call_number, '+62', '0') = :callNumber", nativeQuery = true)
    long countByCallNumber(@Param("callNumber") String callNumber);

    @Query(value = "SELECT u.*, r.name AS role_name FROM ms_user AS u "
        + "JOIN ms_role AS r ON u.role_id = r.id "
        + "WHERE u.rec_status = '"
        + GlobalConstant.REC_STATUS_NON_ACTIVE + "'", nativeQuery = true)
        List<UserEntity> findUsersByRoleNameWhereNoActive(@Param("roleName") String roleName);

    @Query(value = "SELECT u.*, r.name AS role_name FROM ms_user AS u "
        + "JOIN ms_role AS r ON u.role_id = r.id "
        + "WHERE r.id = ?1", nativeQuery = true)
    List<UserEntity> findUsersByRoleId(String roleId);

    @Modifying
    @Query(value = "UPDATE ms_user SET rec_status = '"
        + GlobalConstant.REC_STATUS_NON_ACTIVE
        + "', deleter_id = ?2 , deleted_date = NOW() "
        + "WHERE id = ?1", nativeQuery = true)
    Integer doDelete(Integer id, Integer deleterId);
}
