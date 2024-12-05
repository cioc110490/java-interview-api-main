package com.talentreef.interviewquestions.takehome.controllers;

import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.services.WidgetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/v1/widgets", produces = MediaType.APPLICATION_JSON_VALUE)

public class WidgetController {

    private final WidgetService widgetService;

    public WidgetController(WidgetService widgetService) {
        Assert.notNull(widgetService, "widgetService must not be null");
        this.widgetService = widgetService;
    }

    /**
     * API to list all Widgets in the system.
     *
     * @return ResponseEntity containing the list of all widgets.
     */
    @GetMapping
    public ResponseEntity<List<Widget>> getAllWidgets() {
        return ResponseEntity.ok(widgetService.getAllWidgets());
    }

    /**
     * API to create a new Widget.
     *
     * @param widget The Widget object provided in the request body.
     * @return ResponseEntity with the created Widget and HTTP 201 status.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Widget> createWidget(@Valid @RequestBody Widget widget) {
        log.info("Creating new widget with name: {}", widget.getName());
        Widget createdWidget = widgetService.createWidget(widget);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWidget);
    }

    /**
     * API to retrieve details of a Widget by its name.
     *
     * @param name The name of the Widget to retrieve.
     * @return ResponseEntity containing the Widget details if found.
     */
    @GetMapping("/{name}")
    public ResponseEntity<Widget> getWidgetByName(@PathVariable String name) {
        log.info("Fetching widget details for name: {}", name);
        Widget widget = widgetService.getWidgetByName(name);
        return ResponseEntity.ok(widget);
    }

    /**
     * API to update a Widget's description or price.
     *
     * @param name   The name of the Widget to update.
     * @param widget The Widget object containing updated fields.
     * @return ResponseEntity with the updated Widget details.
     */
    @PutMapping(value = "/{name}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Widget> updateWidget(@PathVariable String name, @Valid @RequestBody Widget widget) {
        log.info("Updating widget with name: {}", name);
        Widget updatedWidget = widgetService.updateWidget(name, widget);
        return ResponseEntity.ok(updatedWidget);
    }

    /**
     * API to delete a Widget by its name.
     *
     * @param name The name of the Widget to delete.
     * @return ResponseEntity with HTTP 204 status on successful deletion.
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteWidget(@PathVariable String name) {
        log.info("Deleting widget with name: {}", name);
        widgetService.deleteWidget(name);
        return ResponseEntity.noContent().build();
    }
}
