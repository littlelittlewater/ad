package ly.repository;

import ly.domain.Mission;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;


@RepositoryRestResource(collectionResourceRel = "rows", path = "mission")
public interface MissionRepository extends PagingAndSortingRepository<Mission, Long> {
  List<Mission> findByCancelEquals(@Param("cancle") Boolean cancle);
}
