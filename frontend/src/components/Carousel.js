import React from "react";
import styled from "styled-components";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

export default function Carousel() {
  const images = [
    {
      id: 0,
      url: "https://ifh.cc/g/xTjM5P.png",
      title: "언제까지 스린이 할래?",
      Content: "입문자를 위한 일타강사의 강의",
      link: "https://ifh.cc/g/poF0CD.png",
      SmallImg: "https://ifh.cc/g/poF0CD.png",
    },
    {
      id: 1,
      url: "https://ifh.cc/g/QW4p6k.png",
      title: "매일 업뎃되는 강의!",
      Content: "매일 업데이트 되는 다양한 강의를 둘러보세요.",
      link: "",
      SmallImg: "https://ifh.cc/g/Q4oshF.png",
    },
    // {
    //   id: 2,
    //   url: "https://ifh.cc/g/T3jpSS.png",
    //   title: "믿고보는 강사진!",
    //   Content: "최고의 경력을 가진 강사진",
    //   link: "/",
    //   SmallImg : ""
    // },
  ];

  const settings = {
    dots: true,
    arrows: true,
    infinite: true,
    speed: 1000,
    autoplay: true,
    autoplaySpeed: 3000,
    pauseOnHover: false,
    slidesToShow: 1,
    slidesToScroll: 1,
  };

  const slideshow = images.map((image) => (
    <ImgZone key={image.id}>
      <Img src={image.url} />
      <ImgLinkZone>
        <SmallImg>
          <img src={image.SmallImg} alt="carousel" />
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
    </>
  );
}

const CarouselWrap = styled.div`
  width: 100%;
  @media screen and (max-width: 1024px) {
    height: 400px;
  }
  @media screen and (max-width: 450px) {
    height: 300px;
  }

  @media screen and (max-width: 400px) {
    height: 200px;
  }

  .slick-dots {
    bottom: 30px;
  }

  .slick-dots li button:before {
    color: #fff;
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
    }
  }

  .slick-prev {
    left: 0px;

    &::before {
      content: "<";
    }
  }

  .slick-next {
    right: 0px;

    &::before {
      content: ">";
    }
  }
`;

const ImgZone = styled.div`
  width: 100%;
  height: 400px;
  position: relative;

  @media screen and (max-width: 1024px) {
    height: 400px;
  }
  @media screen and (max-width: 450px) {
    height: 300px;
  }

  @media screen and (max-width: 400px) {
    height: 200px;
  }
`;

const Img = styled.img`
  width: 100%;
  object-fit: cover;
`;

const ImgLinkZone = styled.div`
  width: 80%;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
`;

const Title = styled.h2`
  color: #fff;
  font-size: 60px;
  font-weight: bold;
`;
const Content = styled.p`
  color: #fff;
  font-size: 20px;
  margin-top: 30px;
`;

const SmallImg = styled.div`
  width: 400px;
  height: 300px;
  float: right;

  img {
    width: 100%;
  }
`;
