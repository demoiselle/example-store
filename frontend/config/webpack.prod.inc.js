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
         'sale' : 'localhost:8080/sales/api/v1/',
         'product' : 'localhost:8080/products/api/v1/',
         'user' : 'localhost:8080/users/api/v1/'
        
    },
    'multitenancy' : {
        'active': true,
        'apiUrl': 'localhost:8080/users/api/v1/'
    }


}