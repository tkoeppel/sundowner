package de.tkoeppel.sundowner.dao;

import de.tkoeppel.sundowner.po.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository

@Repository
interface UserDAO : JpaRepository<UserPO, Long> {

}
