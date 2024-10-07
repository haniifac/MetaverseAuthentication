/**
 * Author: dendy
 * Date:26/09/2024
 * Time:8:40
 * Description:
 */

package org.ukdw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ukdw.entity.StudentEntity;

public interface StudentRepository extends JpaRepository<StudentEntity,String> {
}
