import axios from 'axios';

export default{
    updateAccount(email){
        return axios.put(`/account/${email}`)
    }
}