$(() => {
	// 클릭한 별의 개수를 저장할 배열
	const starCounts = [];

	// 모든 별 아이콘을 순회하면서 클릭 이벤트를 추가
	$('.star').click((context) => {
		const target_star = $(context.currentTarget);
		const stars = [...target_star.parent().children()];
		const star_index = $(stars).index(target_star);
		const emptyStar_class = "star bi bi-star";
		const fullStar_class = "star bi bi-star-fill";

		// 빈 별 클릭 시 채워진 별의 개수 증가
		if (target_star[0].className === emptyStar_class) {
			starCounts[star_index] = starCounts[star_index] + 1 || 1;
			for (let i = 0; i <= star_index; ++i) {
				stars[i].className = fullStar_class;
			}
		} else {
			// 채워진 별 클릭 시 채워진 별의 개수 감소
			for (let i = 4; i >= star_index; --i) {
				if (star_index === 0) {
					break;
				}
				stars[i].className = emptyStar_class;
			}
			starCounts[star_index] -= 1;
			console.log(starCounts);
		}
	});
	
$('#score_btn').click(() => {
		
		const party_id = $('#score_btn').data("party_id");	
		var data = {
			party_id: party_id, 
			member_id: [],
			score: []
				
		};
		
		const id_list = $('.member_id');
		const boxs = $('.star-container');
		
		console.log(boxs);
		$.each(boxs, (index, box) => {
			const fullStar_list = $(box).find('.bi-star-fill');
			console.log(fullStar_list);
			data.score.push(fullStar_list.length);
			data.member_id.push(id_list.eq(index).val());
		});
		
		console.log(data);
		
		const url = '/meokjang/score';
		
		fetch(url, {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify(data),
		})
		.then(response => {
			return response.json();
		})
		.then(json => {
			console.log(json);
			if(json.result == "success") {
				alert(json.message);
				location.href = '/meokjang/mylist';
			}
		})
	});
	
});

	
	




