/* jshint esversion: 6 */

var input = document.getElementById("searchbox");
input.addEventListener("keyup", function(event){
    if (event.keyCode === 13) {
        event.preventDefault();
        document.getElementById("searchbutton").click();
    }
})

function onEnter(){
document.getElementById('searchbutton').onclick = () => {
    fetch("/search?q=" + document.getElementById('searchbox').value)
    .then((response) => response.json())
    .then((data) => {
        document.getElementById("responsesize").innerHTML = 
            "<p>" + data.length + " websites retrieved</p>";
        let results = data.map((page) =>
            `<li><a href="${page.url}">${page.title}</a></li>`)
            .join("\n");
        document.getElementById("urllist").innerHTML = 
        `<ul>${results}</ul>`;
        if (results.length === 0){
           // reload();
            document.getElementById("urllist").innerHTML = `<p>No web page contains the query word.</p>`;
        }
    });
  };
}

function despair(){
    var myArrayMembers = ['Balasz,', 'Selim,', 'Rares,', 'Nicolai,']
    const randomElement = myArrayMembers[Math.floor(Math.random() * myArrayMembers.length)];
    alert(randomElement + " it is your turn to fix the problem.")
}
// function reload(){
//     let container = document.getElementById("urllist");
//     let content = container.innerHTML;
//     container.innerHTML= content; 
// }
