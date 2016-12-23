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
         'sale' : 'http://localhost:8080/sales/api/v1/',
         'product' : 'http://localhost:8080/products/api/v1/',
         'user' : 'http://localhost:8080/users/api/v1/'
        
    },
    'multitenancy' : {
        'active': true,
        'apiUrl': 'http://localhost:8080/users/api/v1/'
    }


}