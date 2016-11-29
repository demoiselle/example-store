module.exports = {
    
    /**
     * Must contain at least one endpoint
     * Example:
     * {
     *      'main' : 'http://localhost/app'
     * }
     * 
     */ 
    'endpoints' : {
        'auth' : 'http://localhost:8080/usuario/api/',
        //'auth' : 'http://supsd.cta.serpro/livraria/api/',
        'livraria' : 'http://supsd.cta.serpro/livraria/api/',
        'usuario' :      'http://localhost:8080/usuario/api/'
        
        
    },
    'multitenancy' : {
        'active': true,
        'apiUrl': 'http://localhost:8080/usuario/api/'
    }


}