const { ApolloServer } = require('apollo-server');
const { ApolloGateway, IntrospectAndCompose, RemoteGraphQLDataSource } = require('@apollo/gateway')

const imsToken = process.env['IMS_TOKEN'];

class AuthenticatedDataSource extends RemoteGraphQLDataSource {
  willSendRequest({ request, context }) {
    request.http.headers.set('X-Arrival-Identity', imsToken);
  }
}

const gateway = new ApolloGateway({
    supergraphSdl: new IntrospectAndCompose({
      subgraphs: [
          { name: 'products', url: 'http://localhost:8082/graphql' },
          { name: 'reviews', url: 'http://localhost:8083/graphql' },
          { name: 'users', url: 'http://localhost:8080/graphql' },
      ],
      introspectionHeaders: {
        'X-Arrival-Identity': imsToken
      }
    }),
    buildService({ name, url }) {
      return new AuthenticatedDataSource({ url });
    }
});

const server = new ApolloServer({
  gateway,
  subscriptions:false,
  tracing: true
});

server.listen().then(({ url }) => {
  console.log(`🚀 Server ready at ${url}`);
});
