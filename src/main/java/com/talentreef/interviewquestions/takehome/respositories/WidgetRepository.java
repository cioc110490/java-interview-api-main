package com.talentreef.interviewquestions.takehome.respositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.talentreef.interviewquestions.takehome.models.Widget;

@Repository
public class WidgetRepository {

  private List<Widget> table = new ArrayList<>();

  /**
   * Deletes a Widget by its name.
   *
   * @param name The name of the Widget to delete.
   * @return The updated list of Widgets after deletion.
   */
  public List<Widget> deleteById(String name) {
    this.table = table.stream()
        .filter((Widget widget) -> !name.equals(widget.getName()))
        .collect(Collectors.toCollection(ArrayList::new));
    return table;
  }

  /**
   * Finds all Widgets in the system.
   *
   * @return A list of all Widgets.
   */
  public List<Widget> findAll() {
    return new ArrayList<>(table); // Return a copy to ensure immutability of the original list.
  }

  /**
   * Finds a Widget by its unique name.
   *
   * @param name The name of the Widget to search for.
   * @return An Optional containing the Widget if found, or empty if not.
   */
  public Optional<Widget> findById(String name) {
    return table.stream()
        .filter((Widget widget) -> name.equals(widget.getName()))
        .findAny();
  }

  /**
   * Saves a Widget to the in-memory database. If a Widget with the same name
   * exists, it is replaced.
   *
   * @param widget The Widget to save.
   * @return The saved Widget.
   */
  public Widget save(Widget widget) {
    deleteById(widget.getName());
    table.add(widget);
    return widget;
  }

  /**
   * Checks if a Widget with the given name exists in the system.
   *
   * @param name The name to check for.
   * @return True if a Widget with the name exists, otherwise false.
   */
  public boolean existsByName(String name) {
    return table.stream()
        .anyMatch(widget -> name.equals(widget.getName()));
  }

  /**
   * Finds a Widget by its unique name. (Alias for consistency with naming
   * conventions.)
   *
   * @param name The name of the Widget to search for.
   * @return An Optional containing the Widget if found, or empty if not.
   */
  public Optional<Widget> findByName(String name) {
    return findById(name); // Reuse existing method for implementation.
  }
}
