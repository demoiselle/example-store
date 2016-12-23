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
        // 'auth' : 'http://localhost:8080/usuario/api/',
        // 'usuario' :      'http://localhost:8080/usuario/api/',
        // 'sale' : 'http://10.32.128.43:8080/store-sale/api/',
        // 'product' : 'http://10.32.128.43:8080/store-product/api/',
        // 'user' : 'http://10.32.128.43:8080/user/api/'


         'sale' : 'localhost:8080/sales/api/v1/',
         'product' : 'localhost:8080/products/api/v1/',
         'user' : 'localhost:8080/users/api/v1/'
        
    },
    'multitenancy' : {
        'active': true,
        //'apiUrl': 'http://localhost:8080/usuario/api/'
        'apiUrl': 'localhost:8080/users/api/v1/'
    }


}