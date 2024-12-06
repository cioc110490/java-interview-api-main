package com.talentreef.interviewquestions.takehome.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.services.WidgetService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WidgetControllerTests {

  final private ObjectMapper objectMapper = new ObjectMapper();

  private MockMvc mockMvc;

  @Mock
  private WidgetService widgetService;

  @InjectMocks
  private WidgetController widgetController;

  @Before
  public void init() {
    mockMvc = MockMvcBuilders.standaloneSetup(widgetController).build();
  }

  /*
   * Test for listing widgets
   */
  @Test
  public void when_getAllWidgets_expect_allWidgets() throws Exception {
    Widget widget = Widget.builder().name("Widget von Hammersmark").build();
    List<Widget> allWidgets = List.of(widget);
    when(widgetService.getAllWidgets()).thenReturn(allWidgets);

    MvcResult result = mockMvc.perform(get("/v1/widgets"))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();

    List<Widget> parsedResult = objectMapper.readValue(result.getResponse().getContentAsString(),
        new TypeReference<List<Widget>>() {
        });
    assertThat(parsedResult).isEqualTo(allWidgets);
  }

  /*
   * Test for creating widgets
   */
  @Test
  public void when_createWidget_expect_createdWidget() throws Exception {
    Widget widget = Widget.builder().name("New Widget").description("Description").price(10.0).build();
    when(widgetService.createWidget(any(Widget.class))).thenReturn(widget);

    MvcResult result = mockMvc.perform(post("/v1/widgets")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(widget)))
        .andExpect(status().isCreated())
        .andDo(print())
        .andReturn();

    Widget createdWidget = objectMapper.readValue(result.getResponse().getContentAsString(), Widget.class);
    assertThat(createdWidget.getName()).isEqualTo("New Widget");
    assertThat(createdWidget.getDescription()).isEqualTo("Description");
    assertThat(createdWidget.getPrice()).isEqualTo(10.0);
  }

  /*
   * Test for getting widget by name
   */
  @Test
  public void when_getWidgetByName_expect_widgetFound() throws Exception {
    Widget widget = Widget.builder().name("Widget1").description("Test Description").price(20.0).build();
    when(widgetService.getWidgetByName("Widget1")).thenReturn(widget);

    MvcResult result = mockMvc.perform(get("/v1/widgets/Widget1"))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();

    Widget retrievedWidget = objectMapper.readValue(result.getResponse().getContentAsString(), Widget.class);
    assertThat(retrievedWidget.getName()).isEqualTo("Widget1");
    assertThat(retrievedWidget.getDescription()).isEqualTo("Test Description");
  }

  /*
   * Test for updating widget
   */
  @Test
  public void when_updateWidget_expect_updatedWidget() throws Exception {
    Widget widget = Widget.builder().name("Widget1").description("Updated Description").price(15.0).build();

    when(widgetService.updateWidget(eq("Widget1"), any(Widget.class))).thenReturn(widget);

    MvcResult result = mockMvc.perform(put("/v1/widgets/Widget1")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(widget)))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();

    Widget updatedWidget = objectMapper.readValue(result.getResponse().getContentAsString(), Widget.class);
    assertThat(updatedWidget.getDescription()).isEqualTo("Updated Description");
    assertThat(updatedWidget.getPrice()).isEqualTo(15.0);
  }

  /*
   * Test for deleting widget
   */
  @Test
  public void when_deleteWidget_expect_success() throws Exception {
    mockMvc.perform(delete("/v1/widgets/Widget1"))
        .andExpect(status().isNoContent())
        .andDo(print());
  }
}