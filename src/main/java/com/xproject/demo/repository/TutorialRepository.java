/**
 * 
 */
package com.xproject.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xproject.demo.models.Tutorial;

/**
 * @author bao.nguyentx
 *
 */
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
    List<Tutorial> findByPublished(boolean published);

    List<Tutorial> findByTitleContaining(String title);
}
