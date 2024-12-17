package com.talentreef.interviewquestions.takehome.services;

import com.talentreef.interviewquestions.takehome.exceptions.WidgetAlreadyExistsException;
import com.talentreef.interviewquestions.takehome.models.PaginatedResult;
import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.respositories.WidgetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
public class WidgetService {

    private final WidgetRepository widgetRepository;

    @Autowired
    public WidgetService(WidgetRepository widgetRepository) {
        Assert.notNull(widgetRepository, "widgetRepository must not be null");
        this.widgetRepository = widgetRepository;
    }

    /**
     * Retrieves all Widgets from the system.
     *
     * @return List of all Widgets.
     */
    public List<Widget> getAllWidgets() {
        return widgetRepository.findAll();
    }

    /**
     * Retrieves a paginated list of widgets.
     *
     * @param page     The current page number.
     * @param pageSize The number of widgets per page.
     * @return         A {@link PaginatedResult} containing widgets and total count.
     */
    public PaginatedResult getPaginatedWidgets(int page, int pageSize) {
        return widgetRepository.findPaginated(page, pageSize);
    }

    /**
     * Creates a new Widget in the system.
     *
     * @param widget The Widget to create.
     * @return       The created Widget.
     */
    public Widget createWidget(Widget widget) {
        log.info("Creating widget with name: {}", widget.getName());
        
        // Check if widget already exists
        if (widgetRepository.existsByName(widget.getName())) {
            throw new WidgetAlreadyExistsException("A Widget with the name '" + widget.getName() + "' already exists.");
        }

        return widgetRepository.save(widget);
    }

    /**
     * Retrieves a Widget by its name.
     *
     * @param name The unique name of the Widget to retrieve.
     * @return     The found Widget.
     * @throws EntityNotFoundException if the Widget is not found.
     */
    public Widget getWidgetByName(String name) {
        log.info("Retrieving widget with name: {}", name);
        return widgetRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Widget with name '" + name + "' not found."));
    }

    /**
     * Updates a Widget's description or price.
     *
     * @param name   The unique name of the Widget to update.
     * @param widget The Widget object containing updated fields.
     * @return       The updated Widget.
     */
    public Widget updateWidget(String name, Widget widget) {
        log.info("Updating widget with name: {}", name);

        Widget existingWidget = widgetRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Widget with name '" + name + "' not found."));

        // Update only description and price
        if (widget.getDescription() != null) {
            existingWidget.setDescription(widget.getDescription());
        }
        if (widget.getPrice() != null) {
            existingWidget.setPrice(widget.getPrice());
        }

        return widgetRepository.save(existingWidget);
    }

    /**
     * Deletes a Widget by its name.
     *
     * @param name The unique name of the Widget to delete.
     * @throws     EntityNotFoundException if the Widget is not found.
     */
    public void deleteWidget(String name) {
        log.info("Deleting widget with name: {}", name);

        Widget widget = widgetRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Widget with name '" + name + "' not found."));

        widgetRepository.deleteById(widget.getName());
        log.info("Widget with name '{}' deleted successfully.", name);
    }
}
