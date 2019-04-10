package ly.repository;

import ly.domain.Mission;
import ly.domain.MissionLog;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(collectionResourceRel = "rows", path = "missionLog")
public interface MissionLogRepository extends PagingAndSortingRepository<MissionLog, Long> {

}
