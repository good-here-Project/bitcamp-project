

const values = location.href.split('?');
if (values.length == 1) {
  alert("URL이 옳지 않습니다.");
  location.href = "list.html";
}

// no=100
const param = values[1].split("=")
if (param.length == 1 || param[0] != 'no') {
  alert("URL이 옳지 않습니다.");
  location.href = "list.html";
}

let no = parseInt(param[1]);
if (isNaN(no)) {
  alert("URL이 옳지 않습니다.");
  location.href = "list.html";
}

fetch("../boards/" + no)
.then(response => {
  return response.json();
})
.then(result => {
  if (result.status == 'failure') {
    alert('게시글을 조회할 수 없습니다.');
    location.href = "list.html";
    return;
  }
  
  let board = result.data;
  //console.log(board);
  document.querySelector("input[name='no']").value = board.no;
  document.querySelector("input[name='title']").value = board.title;
  document.querySelector("textarea[name='content']").value = board.content;
  document.querySelector("#f-writer-name").innerHTML = board.writer.name;
  document.querySelector("#f-created-date").innerHTML = board.createdDate;
  document.querySelector("#f-view-count").innerHTML = board.viewCount;
  
  let ul = "";
  board.attachedFiles.forEach(file => {
    console.log(file);
    if (file.no == 0) return;
    let html = `
      <li id="li-${file.no}">
        <a href="../download/boardfile?fileNo=${file.no}">${file.originalFilename}</a>
        [<a href="#" onclick="deleteFile(${board.no}, ${file.no}); return false;">삭제</a>]
      </li>`;
    ul += html;
  });
  document.querySelector("#f-files").innerHTML = ul;
  
  checkOwner(board.writer.no);
});

function checkOwner(writerNo) {
  fetch("../auth/user")
  .then(response => {
    return response.json();
  })
  .then(result => {
  	console.log(result);
    if (result.status == 'success') {
      if (result.data.no == writerNo) {
        document.querySelector('#btn-update').classList.remove('guest');
        document.querySelector('#btn-delete').classList.remove('guest');
      }
    }
  })
  .catch(exception => {
    alert("로그인 사용자 정보 조회 중 오류 발생!");
    console.log(exception);
  });
}

function deleteFile(boardNo, fileNo) {
  fetch("../boards/" + boardNo + "/files/" + fileNo, {
    method: "DELETE"
  })
  .then(response => {
    return response.json();
  })
  .then(result => {
    if (result.status == 'success') {
      let li = document.querySelector('#li-' + fileNo);
      document.querySelector("#f-files").removeChild(li);
    } else {
      alert('파일 삭제 실패!');
    }
  })
  .catch(exception => {
    alert('파일 삭제 중 오류 발생!');
    console.log(exception);
  });
}


document.querySelector('#btn-list').onclick = function() {
  location.href = 'list.html';
}

document.querySelector('#btn-update').onclick = function() {
  const form = document.querySelector('#board-form');
  const formData = new FormData(form);
  
  fetch("../boards/" + document.querySelector("input[name='no']").value, {
    method: "PUT",
    body: formData
  })
  .then(response => {
    return response.json();
  })
  .then(result => {
    if (result.status == 'success') {
      location.href = 'list.html';
    } else {
      alert('변경 실패!');
    }
  })
  .catch(exception => {
	  alert('변경 중 오류 발생!');
	  console.log(exception);
  });
}
	
document.querySelector('#btn-delete').onclick = function() {
  fetch("../boards/" + document.querySelector('input[name="no"]').value, {
    method: "DELETE"
  })
  .then(response => {
    return response.json();
  })
  .then(result => {
    if (result.status == 'success') {
      location.href = 'list.html';
    } else {
      alert('삭제 실패!');
    }
  })
  .catch(exception => {
    alert('삭제 중 오류 발생!');
    console.log(exception);
  });
}