import React from "react";
import styled from "styled-components";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

export default function Carousel() {
  const images = [
    {
      id: 0,
      url: "https://static.starcraft.com/production/images/content/reborn/carousal-planet-bg.da7b4ef66cd571f0d96de009649e5cad1984c5f1.jpg",
      title: "언제까지 스린이 할래?",
      Content: "입문자를 위한 일타강사의 강의",
    },
    {
      id: 1,
      url: "https://d3muzvmm7fikyw.cloudfront.net/original/1X/e8de17b2ba0f32e4d1bf4d3485ee2e518245c825.jpg",
      title: "매일 업뎃되는 강의!",
      Content: "매일 업데이트 되는 다양한 강의를 둘러보세요.",
    },
    {
      id: 2,
      url: "https://static.starcraft.com/production/images/content/reborn/carousal-terran-bg.e9dd2c91f5bd8bf343a47db26383c48f5cb7913c.jpg",
      title: "믿고보는 강사진!",
      Content: "최고의 경력을 가진 강사진",
    },
  ];

  const settings = {
    dots: true,
    arrows: true,
    infinite: true,
    speed: 1000,
    autoplay: true,
    autoplaySpeed: 3500,
    pauseOnHover: false,
    slidesToShow: 1,
    slidesToScroll: 1,
    fade: true,
  };

  const slideshow = images.map((image) => (
    <ImgZone key={image.id}>
      <Img src={image.url} />
      <ImgLinkZone>
        <SmallImg>
          {image.SmallImg === undefined ? null : (
            <img src={image.SmallImg} alt="carousel" />
          )}
        </SmallImg>
        <Title>{image.title} </Title>
        <Content>{image.Content}</Content>
      </ImgLinkZone>
    </ImgZone>
  ));

  return (
    <>
      <CarouselWrap>
        <Slider {...settings}>{slideshow}</Slider>
      </CarouselWrap>
      <Effect />
    </>
  );
}
const Effect = styled.div`
  width: 100%;
  height: 15px;
  background-image: url("https://static.starcraft.com/production/images/site/dividers/divider-terran.dbf9b7c1f616bf3bc680ed70a7d8de85a44e1e59.jpg");
`;
const CarouselWrap = styled.div`
  width: 100%;
  margin: 0 auto;
<<<<<<<< HEAD:frontend/src/Home/Carousel.js
========
  @media screen and (max-width: 1024px) {
    height: 400px;
  }
  @media screen and (max-width: 450px) {
    height: 300px;
  }

  @media screen and (max-width: 400px) {
    height: 200px;
  }
>>>>>>>> frontend-feat/races:frontend/src/components/Carousel.js

  .slick-dots {
    bottom: 10px;
  }

  .slick-dots li button:before {
    color: #80ff66;
  }

  .slick-arrow {
    z-index: 10;
    width: 50px;
    height: 100%;
    &:hover {
      background: #1b1b1bab;

      transition: 0.7s;
      &::before {
        color: rgba($bk, 0.5);
      }
    }
    &::before {
      font-family: "Gill Sans", "Gill Sans MT", Calibri, "Trebuchet MS",
        sans-serif;
      font-weight: 900;
      font-size: 30px;
      transition: all 0.5s;

      @media screen and (max-width: 855px) {
        font-size: 20px;
      }
    }
  }

  .slick-prev {
    left: 0px;

    &::before {
      content: "<";
      color: #80ff66;
    }
  }

  .slick-next {
    right: 0px;

    &::before {
      content: ">";
      color: #80ff66;
    }
  }
`;

const ImgZone = styled.div`
  width: 100%;
  height: 300px;
  @media screen and (max-width: 660px) {
    height: 200px;
  }
  position: relative;
`;

const Img = styled.img`
  width: 100%;
  height: 400px;
  object-fit: cover;
`;

const ImgLinkZone = styled.div`
  width: 70%;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  @media screen and (max-width: 1024px) {
    width: 90%;
    margin-left: 20px;
  }
`;

const Title = styled.h2`
  padding-top: 40px;
  color: #fff;

<<<<<<<< HEAD:frontend/src/Home/Carousel.js
  font-size: 30px;
========
  font-size: 40px;
>>>>>>>> frontend-feat/races:frontend/src/components/Carousel.js

  @media screen and (max-width: 1600px) {
    font-size: 30px;
  }
  @media screen and (max-width: 860px) {
    font-size: 20px;
  }

  @media screen and (max-width: 550px) {
    font-size: 15px;
  }
`;
const Content = styled.p`
  color: #fff;
  font-size: 20px;
  margin-top: 30px;

  @media screen and (max-width: 860px) {
    font-size: 16px;
  }

  @media screen and (max-width: 550px) {
    font-size: 12px;
  }
`;

const SmallImg = styled.div`
  width: 300px;
  float: right;
  @media screen and (max-width: 1024px) {
    width: 300px;
  }

  img {
    width: 100%;
    float: right;

    @media screen and (max-width: 1600px) {
      width: 80%;
    }

    @media screen and (max-width: 570px) {
      display: none;
    }
  }
`;
