package com.deepoove.poi.tl.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.keyvalue.DefaultKeyValue;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.policy.HackLoopTableRenderPolicy;

import io.swagger.models.ArrayModel;
import io.swagger.models.HttpMethod;
import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.Path;
import io.swagger.models.RefModel;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.AbstractSerializableParameter;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.RefProperty;
import io.swagger.parser.SwaggerParser;

public class SwaggerToWordExample {

    @Test
    public void testSwaggerToWord() throws IOException {
        SwaggerParser swaggerParser = new SwaggerParser();
        Swagger swagger = swaggerParser.read("https://petstore.swagger.io/v2/swagger.json");

        SwaggerView viewData = convert(swagger);
        System.out.println(viewData);
        
        HackLoopTableRenderPolicy hackLoopTableRenderPolicy = new HackLoopTableRenderPolicy();
        Configure config = Configure.newBuilder().bind("parameters", hackLoopTableRenderPolicy)
                .bind("responses", hackLoopTableRenderPolicy)
                .bind("properties", hackLoopTableRenderPolicy).build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/swagger/swagger.docx", config).render(viewData);
        template.writeToFile("out_example_swagger.docx");
    }

    @SuppressWarnings("rawtypes")
    private SwaggerView convert(Swagger swagger) {
        SwaggerView view = new SwaggerView();
        view.setInfo(swagger.getInfo());
        view.setBasePath(swagger.getBasePath());
        view.setExternalDocs(swagger.getExternalDocs());
        view.setHost(swagger.getHost());
        view.setSchemes(swagger.getSchemes());
        List<Resource> resources = new ArrayList<>();
        
        List<Endpoint> endpoints = new ArrayList<>();
        Map<String, Path> paths = swagger.getPaths();
        if (null == paths) return view;
        paths.forEach((url, path) -> {
            
            if (null == path.getOperationMap()) return;
            path.getOperationMap().forEach((method, operation) -> {
                Endpoint endpoint = new Endpoint();
                endpoint.setUrl(url);
                endpoint.setHttpMethod(method.toString());
                endpoint.setGet(HttpMethod.GET == method);
                endpoint.setDelete(HttpMethod.DELETE == method);
                endpoint.setPut(HttpMethod.PUT == method);
                endpoint.setPost(HttpMethod.POST == method);
                
                endpoint.setSummary(operation.getSummary());
                endpoint.setDescription(operation.getDescription());
                endpoint.setTag(operation.getTags());
                List<String> produces = operation.getProduces();
                if (null != produces) {
                    List<DefaultKeyValue<String, String>> pros = new ArrayList<>();
                    produces.forEach(produce -> {
                        pros.add(new DefaultKeyValue<String, String>("key", produce));
                    });
                    endpoint.setProduces(pros);
                }
                List<String> consumes = operation.getConsumes();
                if (null != consumes) {
                    List<DefaultKeyValue<String, String>> coms = new ArrayList<>();
                    consumes.forEach(comsume -> {
                        coms.add(new DefaultKeyValue<String, String>("key", comsume));
                    });
                    endpoint.setConsumes(coms);
                }
                
                if (null != operation.getParameters()) {
                    List<Parameter> parameters = new ArrayList<>();
                    operation.getParameters().forEach(para -> {
                        Parameter parameter = new Parameter();
                        parameter.setDescription(para.getDescription());
                        parameter.setIn(para.getIn());
                        parameter.setName(para.getName());
                        parameter.setRequired(para.getRequired());
                        StringBuilder schema = new StringBuilder();
                        if (para instanceof AbstractSerializableParameter) {
                            Property items = ((AbstractSerializableParameter) para).getItems();
                            // if array
                            schema.append(formatProperty(items));

                            // parameter type or array
                            if (StringUtils.isNotBlank(((AbstractSerializableParameter) para).getType())) {
                                schema.append(((AbstractSerializableParameter) para).getType());
                            }
                            if (StringUtils.isNotBlank(((AbstractSerializableParameter) para).getCollectionFormat())) {
                                schema.append("(").append(((AbstractSerializableParameter) para).getCollectionFormat()).append(")");
                            }
                        }
                        if (para instanceof BodyParameter) {
                            Model schemaModel = ((BodyParameter) para).getSchema();
                            schema.append(fomartSchemaModel(schemaModel));
                        }
                        parameter.setSchema(schema.toString());
                        parameters.add(parameter);
                    });
                    endpoint.setParameters(parameters);
                }
                
                if (null != operation.getResponses()) {
                    List<Response> responses = new ArrayList<>();
                    operation.getResponses().forEach((code, resp) -> {
                        Response response = new Response();
                        response.setCode(code);
                        response.setDescription(resp.getDescription());
                        Model schemaModel = resp.getResponseSchema();
                        response.setSchema(fomartSchemaModel(schemaModel));
                        
                        if (null != resp.getHeaders()) {
                            List<Header> headers = new ArrayList<>();
                            resp.getHeaders().forEach((name, property) -> {
                                Header header = new Header();
                                header.setName(name);
                                header.setDescription(property.getDescription());
                                header.setType(property.getType());
                                headers.add(header);
                            });
                            response.setHeaders(headers);
                        }
                        responses.add(response);
                    });
                    endpoint.setResponses(responses);
                }
                endpoints.add(endpoint);
            }); 
            
        });
        
        swagger.getTags().forEach(tag -> {
            Resource resource = new Resource();
            resource.setName(tag.getName());
            resource.setDescription(tag.getDescription());
            resource.setEndpoints(endpoints.stream().filter(path -> (null != path.getTag() 
                    && path.getTag().contains(tag.getName()))).collect(Collectors.toList()));
            resources.add(resource);
        });
        view.setResources(resources );
        
        if (null != swagger.getDefinitions()) {
            List<Definition> definitions = new ArrayList<>();
            swagger.getDefinitions().forEach((name, model) -> {
                Definition definition = new Definition();
                definition.setName(name);
                if (null != model.getProperties()) {
                    List<com.deepoove.poi.tl.example.Property> properties = new ArrayList<>();
                    model.getProperties().forEach((key, prop) -> {
                        com.deepoove.poi.tl.example.Property property = new com.deepoove.poi.tl.example.Property();
                        property.setName(key);
                        property.setDescription(prop.getDescription());
                        property.setRequired(prop.getRequired());
                        property.setSchema(formatProperty(prop));
                        properties.add(property);
                    });
                    definition.setProperties(properties );
                }
                
                definitions.add(definition);
            });
            view.setDefinitions(definitions);
        }
        return view;
    }

    private String fomartSchemaModel(Model schemaModel) {
        if (null == schemaModel) return "";
        StringBuilder schema = new StringBuilder();
        // if array
        if (schemaModel instanceof ArrayModel) {
            Property items = ((ArrayModel) schemaModel).getItems();
            schema.append(formatProperty(items));
            schema.append(((ArrayModel) schemaModel).getType());
        } else
        // if ref
        if (schemaModel instanceof RefModel) {
            schema.append(((RefModel) schemaModel).get$ref());
        } else if (schemaModel instanceof ModelImpl) {
            schema.append(((ModelImpl) schemaModel).getType());
        } 
        // ComposedModel
        return schema.toString();
    }

    private String formatProperty(Property items) {
        StringBuilder schema = new StringBuilder();
        if (null != items) {
            if (items instanceof RefProperty) {
                schema.append("<").append(((RefProperty) items).get$ref()).append(">");
            } else
            if (items instanceof ArrayProperty) {
                // should recursive
                Property insideItems = ((ArrayProperty) items).getItems();
                if (insideItems instanceof RefProperty) {
                    schema.append("<").append(((RefProperty) insideItems).get$ref()).append(">");
                }
                schema.append(items.getType());
            } else {
                schema.append(items.getType());
            }
        }
        return schema.toString();
    }
    
    public static void main(String[] args) {
        Arrays.asList("1", "2").forEach(a -> {
            return;
        });
        System.out.println("123555");
    }

}
