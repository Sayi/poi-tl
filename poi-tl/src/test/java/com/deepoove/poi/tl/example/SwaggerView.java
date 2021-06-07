package com.deepoove.poi.tl.example;

import java.util.List;

import com.deepoove.poi.data.BookmarkTextRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.plugin.highlight.HighlightRenderData;

import io.swagger.models.ExternalDocs;
import io.swagger.models.Info;
import io.swagger.models.Scheme;

public class SwaggerView {

    protected String swagger = "2.0";
    protected Info info;
    protected String host;
    protected String basePath;
    protected List<Scheme> schemes;
    protected ExternalDocs externalDocs;
    protected List<Resource> resources;
    protected List<Definition> definitions;

    public String getSwagger() {
        return swagger;
    }

    public void setSwagger(String swagger) {
        this.swagger = swagger;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public List<Scheme> getSchemes() {
        return schemes;
    }

    public void setSchemes(List<Scheme> schemes) {
        this.schemes = schemes;
    }

    public ExternalDocs getExternalDocs() {
        return externalDocs;
    }

    public void setExternalDocs(ExternalDocs externalDocs) {
        this.externalDocs = externalDocs;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

}

class Resource {
    private String name;
    private String description;
    private List<Endpoint> endpoints;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Endpoint> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(List<Endpoint> endpoints) {
        this.endpoints = endpoints;
    }

}

class Endpoint {
    private List<String> tag;
    private String summary;
    private String httpMethod;
    private boolean isGet;
    private boolean isPut;
    private boolean isPost;
    private boolean isDelete;
    private String url;
    private String description;
    private List<Parameter> parameters;
    private List<Response> responses;
    private List<String> produces;
    private List<String> consumes;

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public boolean isGet() {
        return isGet;
    }

    public void setGet(boolean isGet) {
        this.isGet = isGet;
    }

    public boolean isPut() {
        return isPut;
    }

    public void setPut(boolean isPut) {
        this.isPut = isPut;
    }

    public boolean isPost() {
        return isPost;
    }

    public void setPost(boolean isPost) {
        this.isPost = isPost;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    public List<String> getProduces() {
        return produces;
    }

    public void setProduces(List<String> produces) {
        this.produces = produces;
    }

    public List<String> getConsumes() {
        return consumes;
    }

    public void setConsumes(List<String> consumes) {
        this.consumes = consumes;
    }

}

class Parameter {
    private String in;
    private String name;
    private boolean required;
    private String description;
    private List<TextRenderData> schema;
    private String defaultValue;

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TextRenderData> getSchema() {
        return schema;
    }

    public void setSchema(List<TextRenderData> schema) {
        this.schema = schema;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

}

class Response {
    private String code;
    private String description;
    private List<TextRenderData> schema;
    private List<Header> headers;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TextRenderData> getSchema() {
        return schema;
    }

    public void setSchema(List<TextRenderData> schema) {
        this.schema = schema;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

}

class Header {
    private String name;
    private String type;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

class Definition {
    private BookmarkTextRenderData name;
    List<Property> properties;
    HighlightRenderData definitionCode;

    public BookmarkTextRenderData getName() {
        return name;
    }

    public void setName(BookmarkTextRenderData name) {
        this.name = name;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public HighlightRenderData getDefinitionCode() {
        return definitionCode;
    }

    public void setDefinitionCode(HighlightRenderData definitionCode) {
        this.definitionCode = definitionCode;
    }

}

class Property {
    private String name;
    private boolean required;
    private List<TextRenderData> schema;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public List<TextRenderData> getSchema() {
        return schema;
    }

    public void setSchema(List<TextRenderData> schema) {
        this.schema = schema;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
