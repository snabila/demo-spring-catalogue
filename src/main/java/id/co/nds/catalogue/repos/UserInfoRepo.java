package id.co.nds.catalogue.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import id.co.nds.catalogue.entities.UserInfoEntity;
import id.co.nds.catalogue.globals.GlobalConstant;

@Repository
@Transactional
public interface UserInfoRepo extends JpaRepository<UserInfoEntity, String>{
    @Query(value = "SELECT  u.*, r.name AS role_name FROM ms_user AS u "
            + "JOIN ms_role AS r ON u.role_id = r.id "
            + "WHERE u.role_id = ?1", nativeQuery = true)
    List<UserInfoEntity> findAllByRole(String roleId);

    @Query(value = "SELECT  u.*, r.name AS role_name FROM ms_user AS u "
            + "JOIN ms_role AS r ON u.role_id = r.id "
            + "WHERE r.rec_status = '"
            + GlobalConstant.REC_STATUS_NON_ACTIVE
            + "' AND LOWER(r.role_name) = LOWER(role_name)", nativeQuery = true)
    List<UserInfoEntity> findUsersByRoleNameWhereNoActive(@Param("role_name") String roleName);

    @Query(value = "SELECT  u.*, r.name AS role_name FROM ms_user AS u "
            + "JOIN ms_role AS r ON u.role_id = r.id "
            + "WHERE r.name = ?1", nativeQuery = true)
    List<UserInfoEntity> findUsersByRoleName( String roleName);
}
