package com.masoud.springbootgraphql.service;

import com.masoud.springbootgraphql.entity.Book;
import com.masoud.springbootgraphql.repository.BookRepository;
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
import java.util.stream.Stream;

@Service
public class GraphQLService {

    @Value("classpath:books.graphql")
    private Resource resource;

    private GraphQL graphQL;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    private AllBookDataFetcher allBookDataFetcher;

    @Autowired
    private BookDataFetcher bookDataFetcher;

    @PostConstruct
    private void loadSchema() throws IOException {
        // Load books list to DB
        LoadDataIntoDB();
        // get the schema
        File schemaFile = resource.getFile();
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();

    }

    private void LoadDataIntoDB() {
        Stream.of(
                new Book(1, "Book1", "1",
                        new String[]{
                                "Author 1-1"
                        }, "date1"),
                new Book(2, "Book2", "1",
                        new String[]{
                                "Author 1-1", "Author1-2"
                        }, "date2"),
                new Book(3, "Book3", "1",
                        new String[]{
                                "Author 1-1", "Author1-2"
                        }, "date3")
        ).forEach(book -> bookRepository.save(book));
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("allBooks", allBookDataFetcher)
                        .dataFetcher("bookDataFetcher", bookDataFetcher))
                .build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }
}
