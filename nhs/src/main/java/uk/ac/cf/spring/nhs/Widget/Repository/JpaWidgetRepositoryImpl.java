package uk.ac.cf.spring.nhs.Widget.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import uk.ac.cf.spring.nhs.Widget.Model.Widget;

@Repository
public class JpaWidgetRepositoryImpl implements WidgetRepositoryInterface {

    @Autowired
    private JpaWidgetRepository jpaWidgetRepository;

    @Override
    public List<Widget> findAll() {
        return jpaWidgetRepository.findAll();
    }

    @Override
    public Optional<Widget> findById(String id) {
        return jpaWidgetRepository.findById(id);
    }

    @Override
    public void save(Widget widget) {
        jpaWidgetRepository.save(widget);
    }

    @Override
    public void delete(Widget widget) {
        jpaWidgetRepository.delete(widget);
    }

    @Override
    public void update(Widget widget) {
        jpaWidgetRepository.save(widget);
    }

}
