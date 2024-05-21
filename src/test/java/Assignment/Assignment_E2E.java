package Assignment;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class Assignment_E2E
{

    @Test
    public void PetID_E2E()
    {
        create_Pet_ID("1011");
        validate_pet_id("1011");
        delete_Pet_Id("1011");


    }

    public void create_Pet_ID(String pet_id)
    {
        //RestAssured.baseURI = "https://petstore.swagger.io/v2/pet";
        Response res =given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"id\": "+pet_id+",\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 0,\n" +
                        "    \"name\": \"string\"\n" +
                        "  },\n" +
                        "  \"name\": \"Tiger\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 0,\n" +
                        "      \"name\": \"string\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}")
                .when()
                .post("https://petstore.swagger.io/v2/pet");
        int Pet_status_code = res.getStatusCode();
        System.out.println("POST validating the Statues code ==> "+Pet_status_code);
        Assert.assertEquals(Pet_status_code, 200);
        String pet_id_res=res.getBody().jsonPath().getString("id");
        String pet_name_res=res.getBody().jsonPath().getString("name");
        Assert.assertEquals(pet_id_res,pet_id);
        System.out.println("the JSON pet id===>"+pet_id_res);
        System.out.println("the JSON pet name===>"+pet_name_res);
    }

    public void validate_pet_id(String pet_id)
    {
        String runtime_pet_id="https://petstore.swagger.io/v2/pet/"+pet_id;
        Response res =given()
                .when()
                .get(runtime_pet_id);
        int get_status_code=res.getStatusCode();
        System.out.println("Get validating the Statues code ==> "+get_status_code);
        Assert.assertTrue(get_status_code==200);
        String pet_id_res=res.getBody().jsonPath().getString("id");
        Assert.assertEquals(pet_id_res,pet_id);
        String pet_status=res.getBody().jsonPath().getString("status");
        Assert.assertEquals(pet_status,"available");
    }

    public void delete_Pet_Id(String pet_id)
    {
        String runtime_pet_id="https://petstore.swagger.io/v2/pet/"+pet_id;
        Response res =given()
                .when()
                .delete(runtime_pet_id);
        int delete_status_code=res.getStatusCode();
        System.out.println("Delete validating the Statues code ==> "+delete_status_code);
        Assert.assertTrue(delete_status_code==200);
        String pet_id_code=res.getBody().jsonPath().getString("code");
        Assert.assertEquals(pet_id_code,Integer.toString(200));
        String pet_id_message=res.getBody().jsonPath().getString("message");
        Assert.assertEquals(pet_id,pet_id_message);

    }



}
