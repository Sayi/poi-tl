package com.deepoove.poi.tl.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.Configure.ELMode;
import com.deepoove.poi.data.HyperLinkTextRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.policy.BookmarkRenderPolicy;
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

/**
 * <p>
 * 无法输入英文引号：
 * </p>
 * <p>
 * "自动更正选项"——"键入时自动套用格式"——"直引号替换为弯引号"（把√去掉）
 * </p>
 * 
 * @author Sayi
 */
@DisplayName("Swagger to Word Example")
public class SwaggerToWordExample {

    // String location = "https://petstore.swagger.io/v2/swagger.json";
    String location = "src/test/resources/swagger/petstore.json";

    @Test
    public void testSwaggerToWord() throws IOException {
        SwaggerParser swaggerParser = new SwaggerParser();
        Swagger swagger = swaggerParser.read(location);
        SwaggerView viewData = convert(swagger);

        HackLoopTableRenderPolicy hackLoopTableRenderPolicy = new HackLoopTableRenderPolicy();
        Configure config = Configure.newBuilder().bind("parameters", hackLoopTableRenderPolicy)
                .bind("responses", hackLoopTableRenderPolicy).bind("properties", hackLoopTableRenderPolicy)
                .addPlugin('>', new BookmarkRenderPolicy()).setElMode(ELMode.SPEL_MODE).build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/swagger/swagger.docx", config)
                .render(viewData);
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
                endpoint.setProduces(operation.getProduces());
                endpoint.setConsumes(operation.getConsumes());

                if (null != operation.getParameters()) {
                    List<Parameter> parameters = new ArrayList<>();
                    operation.getParameters().forEach(para -> {
                        Parameter parameter = new Parameter();
                        parameter.setDescription(para.getDescription());
                        parameter.setIn(para.getIn());
                        parameter.setName(para.getName());
                        parameter.setRequired(para.getRequired());
                        List<TextRenderData> schema = new ArrayList<>();
                        if (para instanceof AbstractSerializableParameter) {
                            Property items = ((AbstractSerializableParameter) para).getItems();
                            // if array
                            schema.addAll(formatProperty(items));

                            // parameter type or array
                            if (StringUtils.isNotBlank(((AbstractSerializableParameter) para).getType())) {
                                schema.add(new TextRenderData(((AbstractSerializableParameter) para).getType()));
                            }
                            if (StringUtils.isNotBlank(((AbstractSerializableParameter) para).getCollectionFormat())) {
                                schema.add(new TextRenderData(
                                        "(" + ((AbstractSerializableParameter) para).getCollectionFormat() + ")"));
                            }
                        }
                        if (para instanceof BodyParameter) {
                            Model schemaModel = ((BodyParameter) para).getSchema();
                            schema.addAll(fomartSchemaModel(schemaModel));
                        }
                        parameter.setSchema(schema);
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
            resource.setEndpoints(
                    endpoints.stream().filter(path -> (null != path.getTag() && path.getTag().contains(tag.getName())))
                            .collect(Collectors.toList()));
            resources.add(resource);
        });
        view.setResources(resources);

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
                    definition.setProperties(properties);
                }

                definitions.add(definition);
            });
            view.setDefinitions(definitions);
        }
        return view;
    }

    private List<TextRenderData> fomartSchemaModel(Model schemaModel) {
        List<TextRenderData> schema = new ArrayList<>();
        if (null == schemaModel) return schema;
        // if array
        if (schemaModel instanceof ArrayModel) {
            Property items = ((ArrayModel) schemaModel).getItems();
            schema.addAll(formatProperty(items));
            schema.add(new TextRenderData(((ArrayModel) schemaModel).getType()));
        } else
            // if ref
            if (schemaModel instanceof RefModel) {
                String ref = ((RefModel) schemaModel).get$ref().substring("#/definitions/".length());
                schema.add(new HyperLinkTextRenderData(ref, "anchor:" + ref));
            } else if (schemaModel instanceof ModelImpl) {
                schema.add(new TextRenderData(((ModelImpl) schemaModel).getType()));
            }
        // ComposedModel
        return schema;
    }

    private List<TextRenderData> formatProperty(Property items) {
        List<TextRenderData> schema = new ArrayList<>();
        if (null != items) {
            if (items instanceof RefProperty) {
                schema.add(new TextRenderData("<"));
                String ref = ((RefProperty) items).get$ref().substring("#/definitions/".length());
                schema.add(new HyperLinkTextRenderData(ref, "anchor:" + ref));
                schema.add(new TextRenderData(">"));
            } else if (items instanceof ArrayProperty) {
                // should recursive
                Property insideItems = ((ArrayProperty) items).getItems();
                if (insideItems instanceof RefProperty) {
                    schema.add(new TextRenderData("<"));
                    String ref = ((RefProperty) insideItems).get$ref().substring("#/definitions/".length());
                    schema.add(new HyperLinkTextRenderData(ref, "anchor:" + ref));
                    schema.add(new TextRenderData(">"));
                }
                schema.add(new TextRenderData(items.getType()));
            } else {
                schema.add(new TextRenderData(items.getType()));
            }
        }
        return schema;
    }

}
