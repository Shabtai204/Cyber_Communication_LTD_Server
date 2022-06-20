window.addEventListener('load', () => {
    
    const id = sessionStorage.getItem('ID');
    const name = sessionStorage.getItem('NAME');
    
    document.getElementById('result-id').innerHTML = id;
    document.getElementById('result-name').innerHTML = name;

})