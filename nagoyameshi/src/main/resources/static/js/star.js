const stars = document.querySelectorAll('.star');
let selectedRating = 0;

stars.forEach((star, index) => {
	star.addEventListener('click', (e) => {
		//クリック位置が星の前半か後半かを判断
		const isHalf = e.offsetX < star.offsetWidth / 2;

		const rating = isHalf ? index + 0.5 : index + 1;
		selectedRating = rating;

		stars.forEach((s, i) => {
			s.classList.remove('active');
			s.classList.remove('half');
		});

		stars.forEach((s, i) => {
			if (i < index || (i === index && !isHalf)) {
				s.classList.add('active');
			} else if (i === index && isHalf) {
				s.classList.add('active', 'half');
			}
		});
	});

	star.addEventListener('mouseover', (e) => {
		const isHalf = e.offsetX < star.offsetWidth / 2;

		stars.forEach((s, i) => {
			s.classList.remove('hover');
			s.classList.remove('half-hover');
		});

		stars.forEach((s, i) => {
			if (i < index || (i === index && !isHalf)) {
				s.classList.add('hover');
			} else if (i === index && isHalf) {
				s.classList.add('hover', 'half-hover');
			}
		});
	});

});

document.querySelector('.rating').addEventListener('mouseleave', () => {
	stars.forEach((s) => {
		s.classList.remove('hover');
		s.classList.remove('half-hover');
	});
	stars.forEach((s, i) => {
		s.classList.remove('active', 'half');
		if (i < Math.floor(selectedRating) || (i === Math.floor(selectedRating) && selectedRating % 1 !== 0)) {
			s.classList.add('active');
		}
		if (i === Math.floor(selectedRating) && selectedRating % 1 !== 0.5) {
			s.classList.add('half');
		}
	});
});
