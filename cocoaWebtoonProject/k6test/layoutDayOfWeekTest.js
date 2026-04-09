import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  vus: 100,          // 동시 사용자 수
  duration: '30s',  // 테스트 시간
};

// 서버 워밍업 (첫 요청 처리)
export function setup() {
  // 단일 요청으로 Spring 초기화
  console.log("캐시 초기 상태로 테스트 시작");
  //http.get('http://localhost:8081/layout?dayOfWeek=1');
}

export default function () {
  const day = Math.floor(Math.random() * 7) + 1; // 랜덤 요일
  const res = http.get(`http://localhost:8081/layout?dayOfWeek=${day}`);

  check(res, {
    'status is 200': (r) => r.status === 200,
    'response < 100ms': (r) => r.timings.duration < 100,
  });

  sleep(1);
}