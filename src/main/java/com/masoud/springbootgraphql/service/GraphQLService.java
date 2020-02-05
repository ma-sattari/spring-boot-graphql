package com.masoud.springbootgraphql.service;

import com.masoud.springbootgraphql.service.datafetcher.AllBookDataFetcher;
import com.masoud.springbootgraphql.service.datafetcher.BookDataFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Service
public class GraphQLService {

    @Value("classpath:books.graphql")
    private Resource resource;

    private GraphQL graphQL;

    @Autowired
    private AllBookDataFetcher allBookDataFetcher;

    @Autowired
    private BookDataFetcher bookDataFetcher;

    @PostConstruct
    private void loadSchema() throws IOException {
        // get the schema
        File schemaFile = resource.getFile();
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();

    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("allBooks", allBookDataFetcher)
                        .dataFetcher("bookDataFetcher", bookDataFetcher))
                .build();
    }

    public GraphQL getGraphQL(){
        return graphQL;
    }
}
