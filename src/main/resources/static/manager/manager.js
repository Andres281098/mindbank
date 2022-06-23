
Vue.createApp({

    data() {
        return {
            clientes:[],
            nombreCliente:"",
            apellidoCliente:"",
            mailCliente:"",
            nombreClienteEditado:"",
            apellidoClienteEditado:"",
            mailClienteEditado:"",
            idCliente:0,
        }
    },

    created() {
        axios.get('http://localhost:8080/rest/clients')
            .then(datos => {

                    this.clientes = datos.data._embedded.clients
            })
    },

    methods: {
        crearCliente(){
            axios.post('http://localhost:8080/rest/clients',{
                nombre:this.nombreCliente,
                apellido:this.apellidoCliente,
                mail:this.mailCliente,
            })
            .then(function(response){
                console.log(response)
            })
            .catch(function(error){
                console.log(error)
            });
            
        },

        borrarCliente(id){
            console.log(id)
                axios.delete(id)
                .then(response =>{
                    this.clientes.splice(id)
                    })
                    .then(function (loadData) {
                        location.reload(loadData);
                    })
                },


    },
    
    computed: {


    
},
}).mount('#app')
