package credit.conveer.msDeal.Repository;


import credit.conveer.msDeal.Model.Employment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmploymentRepository extends JpaRepository<Employment,Long> {
}
