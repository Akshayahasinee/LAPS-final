package com.laps.app.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.laps.app.model.LeaveDetails;

@Repository
public interface ManagerLeaveRepository extends JpaRepository<LeaveDetails, String> {
	@Query("SELECT LD FROM LeaveDetails LD JOIN Employee E ON LD.employeeId = E.employeeId WHERE E.managerId =:managerId AND (LD.leavestatus =:status or LD.leavestatus =:status1)")
	public List<LeaveDetails> GetAllAppliedLeaves(@Param("status") Enum status,@Param("status1") Enum status1, @Param("managerId") Integer managerId);

	@Query("SELECT l FROM LeaveDetails l WHERE l.id=:id")
	public LeaveDetails findByLeaveFormId(@Param("id") Integer id);

	@Modifying
	@Query("UPDATE LeaveDetails l SET l.leavestatus=:status WHERE l.id=:id")
	@Transactional
	public void UpdateLeaveDetailsStatus(@Param("id") Integer id, @Param("status") String status);
	
	@Query("SELECT l from LeaveDetails l WHERE l.employeeId = :eid")
	  List<LeaveDetails> findLeavesByEID(@Param("eid") Integer eid);
}
