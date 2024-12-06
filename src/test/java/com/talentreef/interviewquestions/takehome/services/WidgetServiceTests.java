package com.talentreef.interviewquestions.takehome.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.respositories.WidgetRepository;

@RunWith(SpringRunner.class)
public class WidgetServiceTests {

  @Mock
  private WidgetRepository widgetRepository;

  @InjectMocks
  private WidgetService widgetService;

  // Test for listing all widgets
  @Test
  public void when_getAllWidgets_expect_findAllResult() throws Exception {
    Widget widget = Widget.builder().name("Widgette Nielson").build();
    List<Widget> response = List.of(widget);
    when(widgetRepository.findAll()).thenReturn(response);

    List<Widget> result = widgetService.getAllWidgets();

    assertThat(result).isEqualTo(response);
  }

  // Test for getting widget by name
  @Test
  public void when_getWidgetByName_found_expect_widget() throws Exception {
    Widget widget = Widget.builder().name("Widget1").description("Test widget").price(10.0).build();
    when(widgetRepository.findByName("Widget1")).thenReturn(Optional.of(widget));

    Widget result = widgetService.getWidgetByName("Widget1");

    assertThat(result).isEqualTo(widget);
  }

  // Test for getting widget by name when the widget doesn't exists
  @Test(expected = EntityNotFoundException.class)
  public void when_getWidgetByName_notFound_expect_error() throws Exception {
    when(widgetRepository.findByName("NonExistentWidget")).thenReturn(Optional.empty());

    widgetService.getWidgetByName("NonExistentWidget");
  }

  // Test for creating new widget
  @Test
  public void when_createWidget_expect_createWidget() throws Exception {
    Widget widget = Widget.builder().name("Widget1").description("Description").price(10.0).build();
    when(widgetRepository.save(any(Widget.class))).thenReturn(widget);

    Widget result = widgetService.createWidget(widget);

    assertThat(result).isEqualTo(widget);
  }

  // Test for updating widget
  @Test
  public void when_saveWidget_existingWidget_expect_updatedWidget() throws Exception {
    Widget existingWidget = Widget.builder().name("Widget1").description("Old Description").price(5.0).build();
    Widget updatedWidget = Widget.builder().name("Widget1").description("Updated Description").price(10.0).build();

    when(widgetRepository.findByName("Widget1")).thenReturn(Optional.of(existingWidget));
    when(widgetRepository.save(any(Widget.class))).thenReturn(updatedWidget);

    Widget result = widgetService.updateWidget("Widget1", updatedWidget);

    assertThat(result.getDescription()).isEqualTo("Updated Description");
    assertThat(result.getPrice()).isEqualTo(10.0);
  }

  // Test for updateWidget when it doesn't exist
  @Test(expected = EntityNotFoundException.class)
  public void when_updateWidget_notFound_expect_error() throws Exception {
    Widget updatedWidget = Widget.builder().name("NonExistentWidget").description("New Description").price(20.0).build();

    when(widgetRepository.findByName("NonExistentWidget")).thenReturn(Optional.empty());

    widgetService.updateWidget("NonExistentWidget", updatedWidget);
  }

  // Test for deleting widget
  @Test
  public void when_deleteWidget_found_expect_success() throws Exception {
    Widget widget = Widget.builder().name("Widget1").build();
    when(widgetRepository.findByName("Widget1")).thenReturn(Optional.of(widget));

    widgetService.deleteWidget("Widget1");

    verify(widgetRepository, times(1)).deleteById("Widget1");
  }

  // Test for deleting widget when it doesn't exists
  @Test(expected = EntityNotFoundException.class)
  public void when_deleteWidget_notFound_expect_error() throws Exception {
    when(widgetRepository.findByName("NonExistentWidget")).thenReturn(Optional.empty());

    widgetService.deleteWidget("NonExistentWidget");
  }
}
