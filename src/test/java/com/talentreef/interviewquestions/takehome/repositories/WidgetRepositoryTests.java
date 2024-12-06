package com.talentreef.interviewquestions.takehome.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.respositories.WidgetRepository;

public class WidgetRepositoryTests {

  private WidgetRepository widgetRepository;

  @Before
  public void setUp() {
    widgetRepository = new WidgetRepository();
  }

  // Test for listing widgets when it is empty
  @Test
  public void when_findAll_expect_emptyList_ifNoWidgets() throws Exception {
    List<Widget> result = widgetRepository.findAll();
    assertThat(result).isEmpty();
  }

  // Test for listing widgets
  @Test
  public void when_findAll_expect_nonEmptyList() throws Exception {
    Widget widget = Widget.builder().name("Widget1").description("Test widget").price(10.0).build();
    widgetRepository.save(widget);

    List<Widget> result = widgetRepository.findAll();
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getName()).isEqualTo("Widget1");
  }

  // Test for create widget
  @Test
  public void when_createWidget_expect_createWidget() throws Exception {
    Widget widget = Widget.builder().name("Widget1").description("Test widget").price(10.0).build();

    Widget result = widgetRepository.save(widget);

    assertThat(result).isNotNull();
    assertThat(result.getName()).isEqualTo("Widget1");
    assertThat(result.getDescription()).isEqualTo("Test widget");
  }

  // Test for updating widget
  @Test
  public void when_saveWidget_existingName_expect_widgetReplaced() throws Exception {
    Widget widget1 = Widget.builder().name("Widget1").description("Test widget 1").price(10.0).build();
    Widget widget2 = Widget.builder().name("Widget1").description("Updated widget").price(15.0).build();

    widgetRepository.save(widget1);
    Widget result = widgetRepository.save(widget2);

    assertThat(result.getDescription()).isEqualTo("Updated widget");
    assertThat(result.getPrice()).isEqualTo(15.0);
  }

  // Test for deleting widget
  @Test
  public void when_deleteById_widgetExists_expect_widgetDeleted() throws Exception {
    Widget widget = Widget.builder().name("Widget1").description("Test widget").price(10.0).build();
    widgetRepository.save(widget);

    widgetRepository.deleteById("Widget1");

    List<Widget> result = widgetRepository.findAll();
    assertThat(result).isEmpty();
  }

  // Test for deleting widget when it doesn't exists
  @Test
  public void when_deleteById_widgetDoesNotExist_expect_noChange() throws Exception {
    Widget widget = Widget.builder().name("Widget1").description("Test widget").price(10.0).build();
    widgetRepository.save(widget);

    widgetRepository.deleteById("NonExistentWidget");

    List<Widget> result = widgetRepository.findAll();
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getName()).isEqualTo("Widget1");
  }

  // Test for finding widget by name
  @Test
  public void when_existsByName_widgetExists_expect_true() throws Exception {
    Widget widget = Widget.builder().name("Widget1").description("Test widget").price(10.0).build();
    widgetRepository.save(widget);

    boolean exists = widgetRepository.existsByName("Widget1");

    assertThat(exists).isTrue();
  }

  // Test for finding widget by name when it doesn't exists
  @Test
  public void when_existsByName_widgetDoesNotExist_expect_false() throws Exception {
    boolean exists = widgetRepository.existsByName("NonExistentWidget");

    assertThat(exists).isFalse();
  }

  // Test for finding widget by name and checking properties
  @Test
  public void when_findByName_widgetExists_expect_widget() throws Exception {
    Widget widget = Widget.builder().name("Widget1").description("Test widget").price(10.0).build();
    widgetRepository.save(widget);

    Optional<Widget> result = widgetRepository.findByName("Widget1");

    assertThat(result).isPresent();
    assertThat(result.get().getName()).isEqualTo("Widget1");
    assertThat(result.get().getDescription()).isEqualTo("Test widget");
  }
}
