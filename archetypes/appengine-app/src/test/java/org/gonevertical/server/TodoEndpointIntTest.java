package org.gonevertical.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.gonevertical.server.entities.Todo;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

/**
 * First start the server then run debug on the test. 
 */
public class TodoEndpointIntTest {

  private String endpointUrl = "http://localhost:8080/_ah/api/todoendpoint/v1/todo";

  @Test
  public void testInsert() {
    Todo item = new Todo();
    item.setName("Brandon");

    Todo newTodo = RestAssured.given().contentType(ContentType.JSON).and().content(item).expect().statusCode(200).when()
        .post(endpointUrl).as(Todo.class);

    assertNotNull(newTodo.getId() != null && newTodo.getId() > 0);
    assertEquals(item.getName(), "Brandon");
  }

  @Test
  public void testList() {
    createMultipleItems(20);

    List<Todo> items = RestAssured.given().param("offset", "10").and().param("limit", "10").expect().when()
        .get(endpointUrl).jsonPath().getList("items");

    assertTrue(items.size() > 0);
  }

  private void createMultipleItems(int howMany) {
    for (int i = 0; i < howMany; i++) {
      Todo item = new Todo();
      item.setName("" + i);

      RestAssured.given().contentType(ContentType.JSON).and().content(item).expect().statusCode(200).when()
          .post(endpointUrl).as(Todo.class);
    }
  }

}
